(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('InsuranceRelationSearch', InsuranceRelationSearch);

    InsuranceRelationSearch.$inject = ['$resource'];

    function InsuranceRelationSearch($resource) {
        var resourceUrl =  'api/_search/insurance-relations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
