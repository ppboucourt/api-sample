(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypePatientProcessSearch', TypePatientProcessSearch);

    TypePatientProcessSearch.$inject = ['$resource'];

    function TypePatientProcessSearch($resource) {
        var resourceUrl =  'api/_search/type-patient-processes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
