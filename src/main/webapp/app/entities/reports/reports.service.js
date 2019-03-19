(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Reports', Reports);

    Reports.$inject = ['$resource', 'DateUtils'];

    function Reports ($resource, DateUtils) {
        var resourceUrl =  'api/reports/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.start_date = DateUtils.convertDateTimeFromServer(data.start_date);
                        data.end_date = DateUtils.convertDateTimeFromServer(data.end_date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
