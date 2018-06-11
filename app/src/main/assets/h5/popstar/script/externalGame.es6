import {Subject} from "rxjs/index";

export const popStarObserver = new Subject()

window.pObserver = popStarObserver

/**
 *
 * @param callback
 * @returns {number}
 */
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
let partnerIsLeave = false
let partnerIsFailed = false
let isFailed = false
let resurgenceNum = 0

setupWebViewJavascriptBridge(function (bridge) {
  _bridge = bridge
  /** Initialize your app here */

  /**
   * 我们在这注册一个js调用OC的方法，不带参数，且不用ObjC端反馈结果给JS
   * */
  // bridge.registerHandler('ToJS', function (responseData) {
  //     alert(responseData)
  // })

  /**
   * S给ObjC提供公开的API，在ObjC端可以手动调用JS的这个API。接收ObjC传过来的参数，且可以回调ObjC
   * */
  bridge.registerHandler('ToJS', function (res, responseCallback) {
    // alert(data.t || 'data.t没有接收到数据')
    let {type, data} = JSON.parse(res)
    switch (type) {
      case 'start':
        break
      case 'sendScore':
        // 设置队友分数
        partnerScore(data)
        break
      case 'save':
        // 救队友
        save()
        break
      case 'leave':
        // 队友离开
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

  /** ANDROID上必须要有，不兼容IOS，必须放在尾部*/
  bridge.init(function (responseData, responseCallback) {
  });
})
// let saveScoreArr = [5000, 10000, 20000, 30000, 50000]   // 奖命阈值
let saveScoreArr = [1500, 5000, 7500, 10000, 12500]   // 奖命  阈值
// let saveScoreArr = [1500, 7000, 15000, 35000, 80000]   // 奖命  阈值
// let saveScoreArr = [1000, 4000, 7000, 10500, 145000]   // 奖命阈值
let saveThreshold = 0
let bonusScoreArr = [10000, 20000, 30000, 50000, 100000] //奖励阈值
let bonusThreshold = 0

popStarObserver.subscribe(res => {
  if (res.start) {
    popstar.popStarObserver = popStarObserver
    /**
     * 开始游戏
     * @type {{type: string, data: {}, game: string}}
     */
    let data = {
      type: 'start',
      data: {},
      game: 'popStar'
    }
    if (_bridge) _bridge.callHandler('ToApp', JSON.stringify(data), function (responseData) {
    })
  }
  if (res.total) {
    let score = getScore()
    let level = getLevel()
    /**
     * 上报分数
     * @type {{type: string, data: {score: *, count: *}, game: string}}
     */
    let data = {
      type: 'sendScore',
      data: {
        score: score,
        level: level,
      },
      game: 'popStar'
      // message: '上报自己的分数'
    }
    if (_bridge) _bridge.callHandler('ToApp', JSON.stringify(data), function (responseData) {
    })
    // 拯救队友
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
          game: 'popStar'
        }
        if (_bridge) _bridge.callHandler('ToApp', JSON.stringify(data), function (responseData) {
        })
        partnerIsFailed = false
      }
    }
  }
  if (res.failed) {
    /**
     * 上报失败
     * @type {{type: string, data: {failed: SyncHook}, game: string}}
     */
    let data = {
      type: 'failed',
      data: {},
      game: 'popStar'
    }
    isFailed = true
    checkOver(data)
  }
  if (res.leave) {
    let data = {
      type: 'leave',
      data: {},
      game: 'popStar'
    }
    if (_bridge) _bridge.callHandler('ToApp', JSON.stringify(data), function (responseData) {
    })
  }
})

/**
 * 暂停游戏
 */
function pause () {
  popstar.pause()
}

/**
 * 恢复游戏
 */
function resume () {
  popstar.resume()
}

/**
 * 开始游戏
 * @param level
 */
function enter (level) {
  popstar.enter(level);
}

/**
 * 下一关
 */
function next () {
  popstar.next();
}

/**
 * 销毁
 */
function destroy () {
  popstar.destroy();
}

/**
 * 获取分数
 * @returns {*}
 */
function getScore () {
  return popstar ? popstar.view.total : 0
}

function getLevel () {
  return popstar ? (popstar.constrol._curLevel + 1) : 0
}

/**
 * 设置分数
 * @param s
 */
function setScore (s) {
  popstar.view.total = s
}

/**
 * 获取目标分数
 * @returns {string|*}
 */
function getGoal () {
  return popstar.view.goal.text
}

/**
 * 设置目标分数
 * @param t
 */
function setGoal (t) {
  popstar.view.setGoal(t)
}

/**
 * 设置关卡
 * @param l
 */
function setLevel (l) {
  popstar.view.setLevel(t)
}

/**
 * 设置队友分数
 * @param s
 */
function partnerScore (s) {
  popstar.view.partnerScore = s.score
}

/**
 * 救队友
 */
function save () {
  if (isFailed) {
    popstar.showCountDown(function () {
      isFailed = false
    })
  } else {
    // resurgenceNum += 1
    popstar.view.resurgenceNum += 1
  }
}

/**
 * 队友已离开
 */
// function partnerLeave () {
//   partnerIsLeave = true
// }

/**
 * 队友失败
 */
// function partnerFailed () {
//   partnerIsFailed = true
// }

/**
 * 检查游戏是否结束
 */
function checkOver (data) {
  if ((popstar.view.resurgenceNum !== 0) && (isFailed === true)) {
    popstar.view.resurgenceNum--
    popstar.showCountDown()
    isFailed = false
  } else {
    if (data) {
      if (_bridge) _bridge.callHandler('ToApp', JSON.stringify(data), function (responseData) {
      })
    }
    if (isFailed) {
      if ((partnerIsLeave || partnerIsFailed)) {
        pause()
        popstar.showOver()
      } else {
        pause()
        popstar.showWait()
      }
    }
  }
}