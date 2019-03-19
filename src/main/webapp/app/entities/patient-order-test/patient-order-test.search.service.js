(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('PatientOrderTestSearch', PatientOrderTestSearch);

    PatientOrderTestSearch.$inject = ['$resource'];

    function PatientOrderTestSearch($resource) {
        var resourceUrl =  'api/_search/patient-order-tests/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
