(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeContactFacilityTypeSearch', TypeContactFacilityTypeSearch);

    TypeContactFacilityTypeSearch.$inject = ['$resource'];

    function TypeContactFacilityTypeSearch($resource) {
        var resourceUrl =  'api/_search/type-contact-facility-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
