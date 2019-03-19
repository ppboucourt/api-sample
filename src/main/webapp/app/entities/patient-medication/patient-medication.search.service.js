(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('PatientMedicationSearch', PatientMedicationSearch);

    PatientMedicationSearch.$inject = ['$resource'];

    function PatientMedicationSearch($resource) {
        var resourceUrl =  'api/_search/patient-medications/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
