(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Glucose', Glucose);

    Glucose.$inject = ['$resource', 'DateUtils'];

    function Glucose ($resource, DateUtils) {
        var resourceUrl =  'api/glucoses/:id';

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
            'byChart': { url: 'api/glucose/chart/:id', method: 'GET', isArray: true },
            'update': { method:'PUT' }
        });
    }
})();
