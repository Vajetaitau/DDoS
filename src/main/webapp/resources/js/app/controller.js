(function () {

    var cv = angular.module('cv');

    cv.controller('MainController', ['$rootScope', '$scope', '$cookieStore', '$state', 'cv_sessionService', 'cv_globalService', 'ngDialog',
        function ($rootScope, $scope, $cookieStore, $state, sessionService, globalService, ngDialog) {

            $scope.getToken = globalService.getToken;
            $scope.login = login;
            $scope.logout = logout;
            $scope.isAuthenticated = globalService.isAuthenticated;
            $scope.open = openDialog;

            //page opening functions
            $scope.openUsersAdminPage = openUsersAdminPage;

            function openUsersAdminPage() {
                $state.go('admin.files');
            }

            function postLoginSuccess(userAuthenticationResponse) {
                var token = userAuthenticationResponse.token;
                globalService.setToken(token);
                $cookieStore.put('X-Auth-Token', token);
                globalService.initializeStompClient();
                openUsersAdminPage();
            }

            function login() {
                var loginInfo = {
                    username: $scope.username,
                    password: $scope.password
                };
                sessionService.login(loginInfo).then(function (userAuthenticationResponse) {
                    postLoginSuccess(userAuthenticationResponse);
                }, function () {
                    alert('Call failed!');
                });
            }

            function logout() {
                removeAuthenticationObject();
                document.getElementById("logoutForm").submit();
            }

            function removeAuthenticationObject() {
                $cookieStore.remove('X-Auth-Token');
                globalService.setToken();
            }

            function openDialog() {
                ngDialog.open({
                    template: 'html/templates/login.html',
                    controller: ['$scope', 'cv_sessionService', 'errorMessageService',
                        function ($s, sessionService, errMsg) {

                            $s.loginForm = {};

                            $s.login = function () {
                                var auth = {
                                    username: $s.loginForm.email,
                                    password: $s.loginForm.password
                                };
                                sessionService.login(auth)
                                    .success(function (userAuthenticationResponse) {
                                        postLoginSuccess(userAuthenticationResponse);
                                        $s.closeThisDialog();
                                    })
                                    .error(function (userAuthenticationResponse) {
                                        $s.errorMessage = errMsg.wrongCredentials;
                                    });
                            };

                            $s.cancel = function () {
                                $modalInstance.dismiss('cancel');
                            };
                        }]
                });
            }

            $scope.$watch('getToken()', function () {
                sessionService.getCurrentUser().then(function (currentUserResponse) {
                    $scope.currentUser = currentUserResponse.currentUser;
                });
            });

        }]);

}());