(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('Order_typeSearch', Order_typeSearch);

    Order_typeSearch.$inject = ['$resource'];

    function Order_typeSearch($resource) {
        var resourceUrl =  'api/_search/order-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
