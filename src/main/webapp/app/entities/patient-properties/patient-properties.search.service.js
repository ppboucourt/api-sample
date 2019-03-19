(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('Patient_propertiesSearch', Patient_propertiesSearch);

    Patient_propertiesSearch.$inject = ['$resource'];

    function Patient_propertiesSearch($resource) {
        var resourceUrl =  'api/_search/patient-properties/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
