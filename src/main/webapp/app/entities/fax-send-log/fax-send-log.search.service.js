(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('FaxSendLogSearch', FaxSendLogSearch);

    FaxSendLogSearch.$inject = ['$resource'];

    function FaxSendLogSearch($resource) {
        var resourceUrl =  'api/_search/fax-send-logs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
