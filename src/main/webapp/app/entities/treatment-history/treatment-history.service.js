(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TreatmentHistory', TreatmentHistory);

    TreatmentHistory.$inject = ['$resource', 'DateUtils'];

    function TreatmentHistory ($resource, DateUtils) {
        var resourceUrl =  'api/treatment-histories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
