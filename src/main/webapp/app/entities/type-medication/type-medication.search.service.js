(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeMedicationSearch', TypeMedicationSearch);

    TypeMedicationSearch.$inject = ['$resource'];

    function TypeMedicationSearch($resource) {
        var resourceUrl =  'api/_search/type-medications/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
