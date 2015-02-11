(function () {

    var cv = angular.module('cv');

    cv.service('cv_globalService', [function () {

        var token;

        var stompClient;
        var connected = false;

        var cv_globalService = {
            isAnonymous: function () {
                return false;
            },
            isAuthenticated: function() {
                return !_.isEmpty(token);
            },
            setToken: function(authenticationToken) {
                token = authenticationToken;
            },
            getToken: function() {
                return token;
            },
            initializeStompClient: function() {
                function successCallback() {
                    connected = true;
                }
                function errorCallback() {
                    connected = false;
                }
                var socket = new SockJS('/k/action/s');
                stompClient = Stomp.over(socket);
                stompClient.connect({}, successCallback, errorCallback);
            },
            checkIfConnected: function() {
                return connected;
            },
            getStompClient: function() {
                return stompClient;
            }
        };

        return cv_globalService;

    }]);

}());
