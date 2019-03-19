(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeTreatmentPlanStatusesSearch', TypeTreatmentPlanStatusesSearch);

    TypeTreatmentPlanStatusesSearch.$inject = ['$resource'];

    function TypeTreatmentPlanStatusesSearch($resource) {
        var resourceUrl =  'api/_search/type-treatment-plan-statuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
