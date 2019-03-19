(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('PatientActionPreSearch', PatientActionPreSearch);

    PatientActionPreSearch.$inject = ['$resource'];

    function PatientActionPreSearch($resource) {
        var resourceUrl =  'api/_search/patient-action-pres/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
