(function () {

    var cv = angular.module('cv');

    cv.factory('cv_sessionInjector', ['cv_globalService', function(globalService) {

        var sessionInjector = {
            request: function(config) {
                if (globalService.isAuthenticated()) {
                    config.headers['X-Auth-Token'] = globalService.getToken();
                }
                return config;
            }
        };

        return sessionInjector;
    }]);

}());
