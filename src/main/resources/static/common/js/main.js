const Util = (function () {
  const prefix = "www.liezh.com_";
  const AUTH_TOKEN_PREFIX = "Bearer ";
  const TOKEN_KEY = "www.liezh.com_shise";
  const USER_INFO_PREFIX = "www.liezh.com_userinfo";

  const Head_Auth_Prefix = 'Authorization';
  // 从localStorage中获取参数
  const storageGetter = function (key) {
    return localStorage.getItem(prefix + key);
  };
  // 设置到localStorage
  const storageSetter = function (key, val) {
    return localStorage.setItem(prefix + key, val);
  };
  const storageRemove = function (key) {
    localStorage.removeItem(prefix + key);
  };
  // 设置Authorization Token到本地
  const authTokenGetter = function() {
    return storageGetter(TOKEN_KEY);
  };
  // 获取Authorization Token
  const authTokenSetter = function(val) {
    return storageSetter(TOKEN_KEY, AUTH_TOKEN_PREFIX + val);
  };
  const authTokenRemove = function() {
    storageRemove(TOKEN_KEY);
  };
  const userInfoSetter = function(val) {
    let userInfoJson = JSON.stringify(val);
      return storageSetter(USER_INFO_PREFIX, userInfoJson);
  };
  const userInfoGetter = function () {
      let userInfo = storageGetter(USER_INFO_PREFIX);
      return JSON.parse(userInfo);
  };
  const userInfoRemove = function() {
    storageRemove(USER_INFO_PREFIX);
  };

  const  loginUserInfoGetter = function() {
        // 获取用户是否登录
        let token = authTokenGetter();
        let userinfo = userInfoGetter();
        if(token !== null && token !== '') {
            // 获取用户信息   如头像，用户名等
            userinfo.isLogin = true;
        } else {
            userinfo.isLogin = false;
        }
        return userinfo;
    };

  // 获取URL中的参数
  const getRequest = function() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
      var str = url.substr(1);
      strs = str.split("&");
      for (var i = 0; i < strs.length; i++) {
        theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
      }
    }
    return theRequest;
  };

  const returnTop = function() {
      var currentScroll = document.documentElement.scrollTop || document.body.scrollTop;
      if (currentScroll > 0) {
          window.requestAnimationFrame(returnTop);
          window.scrollTo (0,currentScroll - (currentScroll/5));
      }
  };

  return {
    storageGetter: storageGetter,
    storageSetter: storageSetter,
    storageRemove: storageRemove,
    getRequest: getRequest,
    authTokenGetter: authTokenGetter,
    authTokenSetter: authTokenSetter,
      authTokenRemove: authTokenRemove,
      userInfoSetter : userInfoSetter,
      userInfoGetter : userInfoGetter,
      userInfoRemove : userInfoRemove,
      loginUserInfoGetter: loginUserInfoGetter,
      Head_Auth_Prefix : Head_Auth_Prefix,

      returnTop : returnTop

  };
})();