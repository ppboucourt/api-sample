(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Patient', Patient);

    Patient.$inject = ['$resource', 'DateUtils'];

    function Patient ($resource, DateUtils) {
        var resourceUrl =  'api/patients/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_birth = DateUtils.convertDateTimeFromServer(data.date_birth);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
