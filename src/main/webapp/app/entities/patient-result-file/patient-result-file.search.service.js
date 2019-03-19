(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('PatientResultFileSearch', PatientResultFileSearch);

    PatientResultFileSearch.$inject = ['$resource'];

    function PatientResultFileSearch($resource) {
        var resourceUrl =  'api/_search/patient-result-files/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
