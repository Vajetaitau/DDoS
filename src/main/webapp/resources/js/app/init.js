(function () {

    var cv = angular.module('cv', [
        'ngRoute',
        'ngCookies',
        'pascalprecht.translate',
//        'http-auth-interceptor',
        'ui.router',
        'ngDialog'/* https://github.com/likeastore/ngDialog */
    ]);

    cv.config(['$httpProvider', 'ngDialogProvider',
        function ($httpProvider, ngDialogProvider) {

            ngDialogProvider.setDefaults({
                className: 'ngdialog-theme-default',
                plain: false,
                showClose: true,
                closeByDocument: true,
                closeByEscape: true,
                appendTo: false,
                cache: false,
                preCloseCallback: function () {
                    console.log('default pre-close callback');
                }
            });

            $httpProvider.interceptors.push('cv_sessionInjector');

        }]);

    cv.run(['$rootScope', '$http', '$location', '$translate', '$cookieStore', 'cv_globalService',
        function ($rootScope, $http, $location, $translate, $cookieStore,  globalService) {

            var XAuthToken = $cookieStore.get('X-Auth-Token');
            if (!_.isEmpty(XAuthToken)) {
                globalService.setToken(XAuthToken);
            }

            $rootScope.toggleLanguage = function () {
                $translate.use(($translate.use() === 'en') ? 'lt' : 'en');
            };

        }]);

}());