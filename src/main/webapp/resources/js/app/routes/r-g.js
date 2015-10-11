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

        $stateProvider
			.state('ddos', {
				'url': '/ddos',
				'templateUrl': 'html/public/ddos.html',
				'controller': 'DDOSController',
				'resolve': {
					sourceList: ['ddosService', function(ddosService) {
						return ddosService.getGroupedSourceIps(2000, 50, 'desc').then(function (response) {
							var sourceList = response.data.list;
							return sourceList;
						})
					}]
				}
			})
		;

    }]);

}());