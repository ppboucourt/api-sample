(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('InsuranceTypeSearch', InsuranceTypeSearch);

    InsuranceTypeSearch.$inject = ['$resource'];

    function InsuranceTypeSearch($resource) {
        var resourceUrl =  'api/_search/insurance-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
