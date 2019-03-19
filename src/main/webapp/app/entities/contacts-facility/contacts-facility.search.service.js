(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ContactsFacilitySearch', ContactsFacilitySearch);

    ContactsFacilitySearch.$inject = ['$resource'];

    function ContactsFacilitySearch($resource) {
        var resourceUrl =  'api/_search/contacts-facilities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
