(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('PatientResultSearch', PatientResultSearch);

    PatientResultSearch.$inject = ['$resource'];

    function PatientResultSearch($resource) {
        var resourceUrl =  'api/_search/patient-results/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
