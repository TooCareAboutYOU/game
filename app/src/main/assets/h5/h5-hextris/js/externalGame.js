window.hexObserve = new rxjs.Subject()
var gameIsStart = false
var partnerScore = 0
var resurgenceNum = 0

function setupWebViewJavascriptBridge (callback) {
  //ANDROID使用
  if (window.WebViewJavascriptBridge) {
    callback(WebViewJavascriptBridge);
  } else {
    document.addEventListener('WebViewJavascriptBridgeReady', function () {
      callback(WebViewJavascriptBridge)
    }, false);
  }
  // IOS使用
  if (window.WVJBCallbacks) {
    return window.WVJBCallbacks.push(callback);
  }
  window.WVJBCallbacks = [callback];
  var WVJBIframe = document.createElement('iframe');
  WVJBIframe.style.display = 'none';
  WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
  document.documentElement.appendChild(WVJBIframe);
  setTimeout(function () {
    document.documentElement.removeChild(WVJBIframe)
  }, 0)
}

var _bridge = null
var partnerIsLeave = false
var partnerIsFailed = false
var isFailed = false
let saveScoreArr = [1500, 2500, 3500, 4500, 6000]   // 奖命阈值
// let saveScoreArr = [100, 200, 300, 400, 500]   // 奖命阈值
let saveThreshold = 0
let bonusScoreArr = [3000, 5000, 7000, 9000, 12000] //奖励阈值
let bonusThreshold = 0
/**
 * 与OC交互的所有JS方法都要放在此处注册，才能调用通过JS调用OC或者让OC调用这里的JS
 * */
setupWebViewJavascriptBridge(function (bridge) {
  _bridge = bridge
  /* Initialize your app here */
  /**
   * 我们在这注册一个js调用OC的方法，不带参数，且不用ObjC端反馈结果给JS：打开本demo对应的博文
   * */
  // bridge.registerHandler('ToJS', function (responseData) {
  //     alert(responseData)
  // })
  /**
   * JS给ObjC提供公开的API，在ObjC端可以手动调用JS的这个API。接收ObjC传过来的参数，且可以回调ObjC
   * */
  bridge.registerHandler('ToJS', function (res, responseCallback) {
    // alert(data.t || 'data.t没有接收到数据')
    var type = JSON.parse(res).type
    var data = JSON.parse(res).data
    switch (type) {
      case 'sendScore':
        // game.partnerScore = data.score
        partnerScore = data.score
        updateScore()
        break
      case 'save':
        save()
        break
      case 'leave':
        partnerIsLeave = true
        checkOver()
        break
      case 'failed':
        partnerIsFailed = true
        checkOver()
        break
      default:
        break
    }
    responseCallback()
  })

  /**
   * ANDROID上必须要有，IOS上开头有就报错，所以必须房子尾部
   * */
  bridge.init(function (responseData, responseCallback) {
  });
})

hexObserve.subscribe(function (res) {
  if (res.start) {
    console.log('gameIsStart', gameIsStart)
    if (!gameIsStart) {
      var data = {
        type: 'start',
        data: {},
        game: 'hexTris'
      }
      if (_bridge) _bridge.callHandler('ToApp', JSON.stringify(data), function (responseData) {
      })
      gameIsStart = true
    }
  }
  if (res.score) {
    var data = {
      type: 'sendScore',
      // 2500 3000 3500 4000 10000
      data: {score: score},
      game: 'hexTris'
    }
    if (_bridge) _bridge.callHandler('ToApp', JSON.stringify(data), function (responseData) {
    })
    if (saveThreshold <= saveScoreArr.length - 1) {
      if (score >= saveScoreArr[saveThreshold]) {
        saveThreshold += 1
        /**
         * 拯救队友
         * @type {{type: string, data: {}, game: string}}
         */
        let data = {
          type: 'save',
          data: {},
          game: 'hexTris'
        }
        if (_bridge) _bridge.callHandler('ToApp', JSON.stringify(data), function (responseData) {
        })
        partnerIsFailed = false
      }
    }
  }
  if (res.leave) {
    var data = {
      type: 'leave',
      data: {},
      game: 'hexTris'
    }
    if (_bridge) _bridge.callHandler('ToApp', JSON.stringify(data), function (responseData) {
    })
  }
  if (res.failed) {
    var data = {
      type: 'failed',
      data: {},
      game: 'hexTris'
    }
    isFailed = true
    // showWaitModel()
    checkOver(data)
  }
})

function save () {
  if (isFailed) {
    isFailed = false
    showCountDown()
  } else {
    resurgenceNum += 1
    $('#resurgenceText').text(resurgenceNum)
  }
}

function updateScore () {
  $('#partnerScoreText').text(partnerScore)
  $('#totalScoreText').text(partnerScore + score)
}

function showWaiting () {
  $('#game-over-model').hide()
  $('#wait-model').hide()
  $('#waiting').show()
  $('#back_to_wait_model').off()
  $('#back_to_wait_model').on('click', function () {
    showWaitModel()
  })
}

var hasTimer = false

function showCountDown (fn) {
  $('#game-over-model').hide()
  $('#wait-model').hide()
  $('#waiting').hide()
  $('#countDown').show()
  var number = 3
  $('#countNumber').text(number)
  var timer = setInterval(function () {
    $('#countNumber').text(--number)
    if (number === 0) {
      if (fn) fn()
      hasTimer = false
      $('#countDown').hide()
      hideGameOverDisplay()
      MainHex.blocks = [[], [], [], [], [], []]
      pause(1)
      clearInterval(timer)
    }
  }, 1000)
  hasTimer = true
}

function hideGameOverDisplay () {
  $("#gameoverscreen").fadeOut();
  $("#buttonCont").fadeOut();
  $("#container").fadeOut();
  $("#socialShare").fadeOut();
}

function showGameOver () {
  $('#wait-model').hide()
  $('#waiting').hide()
  $('#game-over-model').show()
  $('#over_btn').on('click', function () {
    hexObserve.next({leave: true})
  })
}

// 检查游戏是否结束
function checkOver (data) {
  // 确保自己没有失败
  if ((resurgenceNum !== 0) && (isFailed === true)) {
    resurgenceNum--
    $('#resurgenceText').text(resurgenceNum)
    showCountDown()
    isFailed = false
  } else {
    if (data) {
      if (_bridge) _bridge.callHandler('ToApp', JSON.stringify(data), function (responseData) {
      })
    }
    if (isFailed) {
      if (partnerIsFailed || partnerIsLeave) {
        showGameOver()
      } else {
        showWaitModel()
      }
    }
  }
}


function showWaitModel () {
  $('#waiting').hide()
  $('#wait-model').show()
  $('#wait_btn').off()
  $('#exit_btn').off()
  $('#wait_btn').on('click', function () {
    showWaiting()
  })

  $('#exit_btn').on('click', function () {
    hexObserve.next({leave: true})
    console.log('213121')
  })
}

