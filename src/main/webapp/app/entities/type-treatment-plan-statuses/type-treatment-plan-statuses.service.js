(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypeTreatmentPlanStatuses', TypeTreatmentPlanStatuses);

    TypeTreatmentPlanStatuses.$inject = ['$resource'];

    function TypeTreatmentPlanStatuses ($resource) {
        var resourceUrl =  'api/type-treatment-plan-statuses/:id';

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
            'update': { method:'PUT' }
        });
    }
})();
