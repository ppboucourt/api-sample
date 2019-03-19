(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('PatientMedicationPresSearch', PatientMedicationPresSearch);

    PatientMedicationPresSearch.$inject = ['$resource'];

    function PatientMedicationPresSearch($resource) {
        var resourceUrl =  'api/_search/patient-medication-pres/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
