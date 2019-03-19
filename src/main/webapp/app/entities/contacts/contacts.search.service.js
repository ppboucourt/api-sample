(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ContactsSearch', ContactsSearch);

    ContactsSearch.$inject = ['$resource'];

    function ContactsSearch($resource) {
        var resourceUrl =  'api/_search/contacts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
