(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypePatientContactTypesSearch', TypePatientContactTypesSearch);

    TypePatientContactTypesSearch.$inject = ['$resource'];

    function TypePatientContactTypesSearch($resource) {
        var resourceUrl =  'api/_search/type-patient-contact-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
