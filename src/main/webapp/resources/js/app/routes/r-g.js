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
						var defaults = ddosService.defaults;
						return ddosService.getGroupedSourceIps(defaults.threshold, defaults.amountToReturn, defaults.source).then(function (response) {
							var sourceList = response.data.list;
							return sourceList;
						})
					}],
					packetCounts: ['ddosService', function(ddosService) {
						var defaults = ddosService.defaults;
						var seriesA = {ip: defaults.seriesAIp, isSource: defaults.seriesASource};
						var seriesB = {ip: defaults.seriesBIp, isSource: defaults.seriesBSource};
						return ddosService.getPacketCount(defaults.timeFrom, defaults.timeTo, defaults.increment, [seriesA, seriesB]).then(function (response) {
							var packetCountsResponse = response.data.list;
							return packetCountsResponse;
						});
					}]
				}
			})
		;

    }]);

}());