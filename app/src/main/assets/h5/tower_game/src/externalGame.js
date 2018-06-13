import {Subject} from "rxjs/index";

export const towerObserver = new Subject()

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

let _bridge = null


// 自己是否失败
let isFailed = false
let partnerIsLeave = false
let partnerIsFailed = false
setupWebViewJavascriptBridge(function (bridge) {
  // const {setToast3} = game.getVariable('GAME_USER_OPTION')
  // setToast3()
  _bridge = bridge
  /* Initialize your app here */
  /**
   * 我们在这注册一个js调用OC的方法，不带参数，且不用ObjC端反馈结果给JS
   * */
  // bridge.registerHandler('ToJS', function (responseData) {
  //     alert(responseData)
  // })

  /**
   * JS给ObjC提供公开的API，在ObjC端可以手动调用JS的这个API。接收ObjC传过来的参数，且可以回调ObjC
   * */
  bridge.registerHandler('ToJS', function (res, responseCallback) {
    // const {setToast1} = game.getVariable('GAME_USER_OPTION')
    // setToast1('js接收' + res)
    // alert(data.t || 'data.t没有接收到数据')
    let {type, data} = JSON.parse(res)
    switch (type) {
      case  'start':
        break
      case 'sendScore':
        game.partnerScore = data.score
        break
      case 'addHeart':
        addHeart()
        // togglePaused()
        break
      case 'leave':
        partnerIsLeave = true
        checkOver()
        break
      case 'failed':
        partnerIsFailed = true
        checkOver()
      default:
        break
    }
    responseCallback()
  })

  /**
   * ANDROID上必须要有，不兼容IOS，必须放在尾部
   * */
  bridge.init(function (responseData, responseCallback) {
  });
})


towerObserver.subscribe(res => {
  // const {setToast2} = game.getVariable('GAME_USER_OPTION')
  if (res.start) {
    game.towerObserver = towerObserver
    /**
     * 开始
     * @type {{type: string, data: {}, game: string}}
     */
    let data = {
      type: 'start',
      data: {},
      game: 'towerGame'
    }
    if (_bridge) {
      _bridge.callHandler('ToApp', JSON.stringify(data), function (responseData) {
      })
      // setToast2('开始游戏')
    }
  }
  if (res.success) {
    if ([5, 15, 25, 35, 45, 55, 65, 75].indexOf(res.success) !== -1) {
      /**
       * 给队友加血
       * @type {{type: string, data: {}, game: string}}
       */
      let data = {
        type: 'addHeart',
        data: {},
        game: 'towerGame'
      }
      if (_bridge) {
        _bridge.callHandler('ToApp', JSON.stringify(data), function (responseData) {
        })
        // setToast2('给队友加血')
      }
      partnerIsFailed = false
    }
  }
  if (res.score) {
    /**
     * 上报分数
     * @type {{type: string, data: {score, count}, game: string}}
     */
    let data = {
      type: 'sendScore',
      data: {
        score: getScore(),
        count: getCount(),
      },
      game: 'towerGame'
      // message: '上报自己的分数'
    }
    if (_bridge) {
      _bridge.callHandler('ToApp', JSON.stringify(data), function (responseData) {
      })
      // setToast2('上报分数')
    }
  }
  if (res.failed) {
    /**
     * 上报游戏失败
     * @type {{type: string, data: {failed: SyncHook}, game: string}}
     */
    let data = {
      type: 'failed',
      data: {
        failed: res.failed
        // message: '心全空'
      },
      game: 'towerGame'
    }
    const {setGameFailed, showWait} = game.getVariable('GAME_USER_OPTION')
    if (partnerIsLeave || partnerIsFailed) {
      if (setGameFailed) setGameFailed(res.failed)
    } else {
      game.togglePaused()
      if (showWait) showWait(res.failed)
    }
    isFailed = true
    if (_bridge) {
      _bridge.callHandler('ToApp', JSON.stringify(data), function (responseData) {
      })
      // setToast2('上报游戏失败')
    }
  }
  if (res.leave) {
    let data = {
      type: 'leave',
      data: {},
      game: 'towerGame'
    }
    if (_bridge) {
      _bridge.callHandler('ToApp', JSON.stringify(data), function (responseData) {
      })
      // setToast2('队友离开游戏')
    }
  }
})

function togglePaused () {
  game.togglePaused()
}

function addHeart () {
  const lastFailedCount = game.getVariable('FAILED_COUNT')
  const {showCountDown} = game.getVariable('GAME_USER_OPTION')
  if (lastFailedCount) {
    const failed = lastFailedCount - 1
    game.setVariable('FAILED_COUNT', failed)
    if (lastFailedCount === 7 && game.paused) {
      showCountDown()
    }
  }
  isFailed = false
}

function reduceHeart () {
  const lastFailedCount = game.getVariable('FAILED_COUNT')
  const failed = lastFailedCount + 1
  game.setVariable('FAILED_COUNT', failed)
}

function gameOver () {
  const {setGameFailed} = game.getVariable('GAME_USER_OPTION')
  if (setGameFailed) setGameFailed(7)  // 参数大于等于7
  game.pauseAudio('bgm')
  game.playAudio('game-over')
  game.setVariable('GAME_START_NOW', false)
}

function getScore () {
  const lastGameScore = game.getVariable('GAME_SCORE')
  return lastGameScore
}

function getCount () {
  const lastSuccessCount = game.getVariable('SUCCESS_COUNT')
  return lastSuccessCount
}

function checkOver () {
  if (isFailed && (partnerIsFailed || partnerIsLeave)) {
    const {setGameFailed} = game.getVariable('GAME_USER_OPTION')
    const failedCount = game.getVariable('FAILED_COUNT')
    setGameFailed(failedCount)
  }
}
document.getElementsByClassName('js-exit')[0].addEventListener('click', function () {
  // console.log('js-exit')
  // console.log(towerObserver)
  towerObserver.next({leave: true})
})
document.getElementsByClassName('js-gameOver')[0].addEventListener('click', function () {
  // console.log('js-exit')
  // console.log(towerObserver)
  towerObserver.next({leave: true})
})