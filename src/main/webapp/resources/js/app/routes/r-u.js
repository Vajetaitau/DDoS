(function () {//registered user routes

    var cv = angular.module('cv');

    cv.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise("/");

        $stateProvider
            .state('user', {
                'url': '/city/:number',
                'templateUrl': 'html/public/city.html',
                'controller': 'CityController'
            })
        ;

    }]);

}());