(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('PatientToShiftSearch', PatientToShiftSearch);

    PatientToShiftSearch.$inject = ['$resource'];

    function PatientToShiftSearch($resource) {
        var resourceUrl =  'api/_search/patient-to-shifts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
