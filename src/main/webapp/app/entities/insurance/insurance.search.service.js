(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('InsuranceSearch', InsuranceSearch);

    InsuranceSearch.$inject = ['$resource'];

    function InsuranceSearch($resource) {
        var resourceUrl =  'api/_search/insurances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
