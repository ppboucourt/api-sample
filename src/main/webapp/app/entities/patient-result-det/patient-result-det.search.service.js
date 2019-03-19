(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('PatientResultDetSearch', PatientResultDetSearch);

    PatientResultDetSearch.$inject = ['$resource'];

    function PatientResultDetSearch($resource) {
        var resourceUrl =  'api/_search/patient-result-dets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
