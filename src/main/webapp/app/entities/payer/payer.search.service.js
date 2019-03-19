(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('PayerSearch', PayerSearch);

    PayerSearch.$inject = ['$resource'];

    function PayerSearch($resource) {
        var resourceUrl =  'api/_search/payers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
