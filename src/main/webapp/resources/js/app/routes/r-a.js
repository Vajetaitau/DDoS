(function () {//admin user routes

    var cv = angular.module('cv');

    cv.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise("/");

        $stateProvider
            .state('admin', {
                'abstract': true,
                'url': '/admin',
                'templateUrl': 'html/private/main.html',
                'controller': 'Admin-Main'
            })
            .state('admin.files', {
                'url': '/files',
                'templateUrl': 'html/private/files.html',
                'controller': 'Admin-Files'
            })
            .state('admin.users', {
                'url': '/users',
                'templateUrl': 'html/private/users.html',
                'controller': 'Admin-Users'
            })
        ;

    }]);

}());