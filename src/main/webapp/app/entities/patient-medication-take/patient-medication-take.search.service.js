(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('PatientMedicationTakeSearch', PatientMedicationTakeSearch);

    PatientMedicationTakeSearch.$inject = ['$resource'];

    function PatientMedicationTakeSearch($resource) {
        var resourceUrl =  'api/_search/patient-medication-takes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
