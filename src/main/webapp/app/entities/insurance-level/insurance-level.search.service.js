(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('InsuranceLevelSearch', InsuranceLevelSearch);

    InsuranceLevelSearch.$inject = ['$resource'];

    function InsuranceLevelSearch($resource) {
        var resourceUrl =  'api/_search/insurance-levels/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
