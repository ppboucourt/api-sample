(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('InsuranceCarrierSearch', InsuranceCarrierSearch);

    InsuranceCarrierSearch.$inject = ['$resource'];

    function InsuranceCarrierSearch($resource) {
        var resourceUrl =  'api/_search/insurance-carriers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
