(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('PatientOrderItemSearch', PatientOrderItemSearch);

    PatientOrderItemSearch.$inject = ['$resource'];

    function PatientOrderItemSearch($resource) {
        var resourceUrl =  'api/_search/patient-order-items/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
