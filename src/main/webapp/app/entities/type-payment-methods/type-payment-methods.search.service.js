(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypePaymentMethodsSearch', TypePaymentMethodsSearch);

    TypePaymentMethodsSearch.$inject = ['$resource'];

    function TypePaymentMethodsSearch($resource) {
        var resourceUrl =  'api/_search/type-payment-methods/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
