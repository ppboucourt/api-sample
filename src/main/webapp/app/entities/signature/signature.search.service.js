(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('SignatureSearch', SignatureSearch);

    SignatureSearch.$inject = ['$resource'];

    function SignatureSearch($resource) {
        var resourceUrl =  'api/_search/signatures/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
