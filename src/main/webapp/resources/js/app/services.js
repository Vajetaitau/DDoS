(function () {

    var cv = angular.module('cv');

    cv.service('authenticationService', ['$rootScope', 'commonService', function ($rootScope, commonService) {

        var authenticationService = {};
        var url = 'action/auth/';

        authenticationService.authenticate = function (auth) {
            return commonService.oneParamPost(url + 'authenticate', auth);
        };

        authenticationService.statusCheck = function (statusCheck) {
            return commonService.oneParamPost('cv/authentication/status-check', statusCheck);
        };

        authenticationService.logout = function (sessionToken) {
            return commonService.oneParamPost('action/authentication/session-end');
        };

        authenticationService.getUser = function (sessionToken) {
            return commonService.oneParamGet('action/authentication/get-user');
        };

        return authenticationService;

    }]);

}());