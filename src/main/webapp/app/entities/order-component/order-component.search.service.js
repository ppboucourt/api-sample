(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('OrderComponentSearch', OrderComponentSearch);

    OrderComponentSearch.$inject = ['$resource'];

    function OrderComponentSearch($resource) {
        var resourceUrl =  'api/_search/order-components/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
