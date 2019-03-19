(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Bed', Bed);

    Bed.$inject = ['$resource'];

    function Bed ($resource) {
        var resourceUrl =  'api/beds/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'byFacility': {url: 'api/beds-facility/:id', method: 'GET', isArray: true},
            'getWithChart': {url: 'api/beds-chart/:id', method: 'GET'},
            'freeBeds': {url: 'api/free-beds/:id/:actualChart', method: 'GET', isArray: true},
            'byBuilding': {url: 'api/beds/building/:id', method: 'GET', isArray: true},
            'getBedsByIds': {url:'api/beds/ids', method: 'POST', isArray: true},
            'update': {method:'PUT'}
        });
    }
})();
