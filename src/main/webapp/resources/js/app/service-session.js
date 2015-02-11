(function () {

    var cv = angular.module('cv');

    cv.service('cv_sessionService', ['$http', function ($http) {

        var cv_sessionService = {
            /**
             *
             * @param loginInfo - object consists of username and password fields
             * @returns {HttpPromise}
             */
            login: function (loginInfo) {
                return $http.post('action/authenticate', loginInfo);
            },
            getCurrentUser: function() {
                return $http.get('action/current-user');
            }
        };

        return cv_sessionService;

    }]);

}());