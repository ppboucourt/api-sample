(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Vitals', Vitals);

    Vitals.$inject = ['$resource', 'DateUtils'];

    function Vitals ($resource, DateUtils) {
        var resourceUrl =  'api/vitals/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                        data.time = DateUtils.convertDateTimeFromServer(data.time);
                    }
                    return data;
                }
            },
            'byChart': { url: 'api/vitals/chart/:id', method: 'GET', isArray: true },
            'update': { method:'PUT' }
        });
    }
})();
