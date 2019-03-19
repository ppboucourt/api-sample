(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypePatientProgramsSearch', TypePatientProgramsSearch);

    TypePatientProgramsSearch.$inject = ['$resource'];

    function TypePatientProgramsSearch($resource) {
        var resourceUrl =  'api/_search/type-patient-programs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
