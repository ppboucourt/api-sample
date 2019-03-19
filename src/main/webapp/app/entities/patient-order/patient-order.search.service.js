(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('PatientOrderSearch', PatientOrderSearch);

    PatientOrderSearch.$inject = ['$resource'];

    function PatientOrderSearch($resource) {
        var resourceUrl =  'api/_search/patient-orders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
