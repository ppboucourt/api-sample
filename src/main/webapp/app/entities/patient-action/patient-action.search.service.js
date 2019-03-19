(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('PatientActionSearch', PatientActionSearch);

    PatientActionSearch.$inject = ['$resource'];

    function PatientActionSearch($resource) {
        var resourceUrl =  'api/_search/patient-actions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
