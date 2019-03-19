(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('OrderFrequencySearch', OrderFrequencySearch);

    OrderFrequencySearch.$inject = ['$resource'];

    function OrderFrequencySearch($resource) {
        var resourceUrl =  'api/_search/order-frequencies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
