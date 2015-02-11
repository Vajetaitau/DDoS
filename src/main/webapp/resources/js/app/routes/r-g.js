(function () {//guest user routes

    var cv = angular.module('cv');

    cv.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise("/");

        $stateProvider
            .state('guest', {
                'url': '/',
                'templateUrl': 'html/public/main.html',
                'controller': 'MainController'
            })
        ;

    }]);

}());