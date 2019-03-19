/**
 * Created by alien on 7/17/17.
 */
'use strict';

angular
    .module('bluebookApp')
    .controller('maskreController', maskreController)
    .directive('maskre', MaskRe);

function MaskRe() {
    return {
        restrict: 'A',
        require: 'ngModel',
        controller: maskreController,
        controllerAs: 'maskreCtrl',
        bindToController: true,
        link: function (scope, $element, attrs, ngModelCtrl) {
            var maskRe = attrs.maskre;
            var maxLength = attrs.ngMaxlength || 0;
            var checkAtLength = attrs.checkAtLength || null;
            var cleanSpecialChars = attrs.cleanSpecialChars || null;
            var disableEnter = attrs.disableEnter || null;
            var disableAltKey = attrs.disableAltKey || null;
            var options = {
                maskRe: maskRe,
                maxLength: maxLength,
                cleanSpecialChars: cleanSpecialChars,
                checkAtLength: checkAtLength,
                disableEnter: disableEnter,
                disableAltKey: disableAltKey
            };

            $element.bind('keypress', function (event) {
                scope.maskreCtrl.validationMaskReCheck(event, $element, ngModelCtrl, options);
            });

            $element.bind('paste', function (event) {
                scope.maskreCtrl.validationOnPaste(event, $element, ngModelCtrl, options);
            });
        }
    };
}


maskreController.$inject = ['lodash'];

function maskreController(_) {
    var maskreCtrl = this;
    //Vars
    maskreCtrl.flags = 'm';
    maskreCtrl.PASTE_EVENT = 'paste';
    maskreCtrl.KEYPRESS_EVENT = 'keypress';

    //Functions
    maskreCtrl.excludeCombinationKeys = excludeCombinationKeys;
    maskreCtrl.verifyLength = verifyLength;
    maskreCtrl.validationMaskReCheck = validationMaskReCheck;
    maskreCtrl.validationMaskRe = validationMaskRe;
    maskreCtrl.validationOnPaste = validationOnPaste;

    /**
     * Change text value at clean special chars
     * @param event
     * @param $element
     * @param ngModelCtrl
     * @param options
     * @param value
     */
    function changeTextValue(event, $element, ngModelCtrl, options, value) {
        //Clean value
        if (!value) {
            if (options.checkAtLength) {
                event.target.value = "";
                ngModelCtrl.$setViewValue("");
            }
        } else {
            //Clean special chars and set text value
            var strShow = value;
            if (options.cleanSpecialChars) {
                event.preventDefault();
                var selectionStart = 0;
                //var validType = (/text|password|search|tel|url/).test($element[0].type);
                //console.log("validType", validType);
                if (event.type == maskreCtrl.KEYPRESS_EVENT) {
                    strShow = (!options.checkAtLength) ? getTextValue($element, value, options) : value;
                    if (!_.isEqual(strShow, $element.val())) {
                        selectionStart = $element[0].selectionStart + 1;
                    }
                }
                event.target.value = strShow;
                ngModelCtrl.$setViewValue(strShow);
                if (selectionStart != 0) {
                    $element[0].selectionStart = selectionStart;
                    $element[0].selectionEnd = selectionStart;
                }
            }
        }
    }

    // Fix: failed to read the 'selectionStart' property from 'HTMLInputElement'
    // The @fn parameter provides a callback to execute additional code
    function fixSelection(dom, fn) {
        var validType = (/text|password|search|tel|url/).test(dom.type),
            selection = {
                start: validType ? dom.selectionStart : 0,
                end: validType ? dom.selectionEnd : 0
            };
        if (validType && fn instanceof Function) fn(dom);
        return selection;
    }

// Gets the current position of the cursor in the @dom element
    function getCaretPosition(dom) {
        var selection, sel;
        if ('selectionStart' in dom) {
            return fixSelection(dom).start;
        } else { // IE below version 9
            selection = document.selection;
            if (selection) {
                sel = selection.createRange();
                sel.moveStart('character', -dom.value.length);
                return sel.text.length;
            }
        }
        return -1;
    }


    /**
     * Get text value on paste event or keypress
     * @param $element
     * @param textValue
     * @param options
     * @returns {*}
     */
    function getTextValue($element, textValue, options) {
        var finalText = $element.val();
        //Remove end line
        if (options.disableEnter) {
            //Replace line break
            textValue = textValue.replace(/(\r\n|\n|\r)/gm, " ");
            //Replace many spaces to one space
            textValue = textValue.replace(/\s+/g, " ");
        }
        //Clean special chars
        if (options.cleanSpecialChars) {
            textValue = cleanUpSpecialChars(textValue);
        }

        //Selection start component
        var startPos = null;
        var endPos = null;
        if ($element[0].selectionStart || $element[0].selectionStart === 0) {
            startPos = $element[0].selectionStart;
            endPos = $element[0].selectionEnd || null;
        }
        //Change text
        if (options.maxLength && textValue.length > options.maxLength) {
            //Check text selected
            var splitLength = (!_.isNull(startPos) && !_.isNull(endPos) && (endPos - startPos > 0)) ? (endPos - startPos) : options.maxLength;
            textValue = textValue.substr(0, splitLength);
        }
        if ((!_.isNull(startPos) && (!_.isNull(startPos) && !_.isNull(endPos) && (endPos - startPos > 0))) ||
            finalText.length < options.maxLength) {
            var startText = finalText.substr(0, startPos);
            var endText = "";
            if (finalText.length > endPos) {
                endText = finalText.substr(-(finalText.length - endPos));
            } else {
                endText = finalText.substr(0, finalText.length - endPos);
            }
            finalText = startText + textValue + endText;
        }
        //Change final text
        if (options.maxLength && finalText.length > options.maxLength) {
            finalText = finalText.substr(0, options.maxLength);
        }
        return finalText;
    }

    /**
     * Validation on paste event fire
     * @param event
     * @param $element
     * @param ngModelCtrl
     * @param options
     */
    function validationOnPaste(event, $element, ngModelCtrl, options) {
        if (options.maskRe) {
            var regExp = new RegExp(options.maskRe, maskreCtrl.flags);
            var pasteData = event.originalEvent.clipboardData.getData('text');
            var valueDataPaste = getTextValue($element, pasteData, options);
            //console.log('Regex: ' + regExp.test(pasteData) + " | pasteData: " + pasteData + " | length: " + valueData.length)
            if (!regExp.test(valueDataPaste)) {
                event.preventDefault();
            }
            //Verify length
            if (!regExp.test(valueDataPaste) && !verifyLength(valueDataPaste, options.maxLength)) {
                event.preventDefault();
            } else {
                if (!verifyLength(valueDataPaste, options.maxLength)) {
                    event.preventDefault();
                    changeTextValue(event, $element, ngModelCtrl, options, valueDataPaste);
                } else {
                    if (!regExp.test(valueDataPaste)) {
                        event.preventDefault();
                    } else {
                        changeTextValue(event, $element, ngModelCtrl, options, valueDataPaste);
                    }
                }
            }
        }
    }

    /**
     * Validation mask on keypress event fire
     * @param event
     * @param $element
     * @param ngModelCtrl
     * @param options
     */
    function validationMaskReCheck(event, $element, ngModelCtrl, options) {
        //If not Check at length
        if (!options.checkAtLength) {
            validationMaskRe(event, $element, ngModelCtrl, options);
        } else {
            var valElLength = $element.val().length;
            //Verify length of element
            if (options.checkAtLength == valElLength + 1) {
                validationMaskRe(event, $element, ngModelCtrl, options);
            }
        }
    }

    /**
     * Validation input with mask defined
     * @param event
     * @param $element
     * @param ngModelCtrl
     * @param options
     */
    function validationMaskRe(event, $element, ngModelCtrl, options) {
        if (options.maskRe) {
            var regExp = new RegExp(options.maskRe, maskreCtrl.flags);
            var keyData = event.keyCode || event.charCode;
            var key = String.fromCharCode(keyData);
            var strValidate = (!options.checkAtLength) ? key : getTextValue($element, key, options);
            //console.log("event", event)
            //console.log('key-> ' + key + ": Keydata->" + keyData + " : " + "maskre-> " + options.maskRe + " =>" + regExp.test(strValidate));
            //Verify regex
            if (!regExp.test(strValidate) && !verifyKeyInExcludes(event) && !excludeCombinationKeys(event, options)) {
                //keypress
                event.preventDefault();
                changeTextValue(event, $element, ngModelCtrl, options);
            } else {
                //Verify length
                if (!verifyKeyInExcludes(event) && !verifyLength($element.val(), options.maxLength) && !excludeCombinationKeys(event, options)) {
                    event.preventDefault();
                    changeTextValue(event, $element, ngModelCtrl, options, strValidate);
                } else {
                    if (!checkValidKey(event, options)) {
                        event.preventDefault();
                    } else {
                        //Set text without special chars
                        changeTextValue(event, $element, ngModelCtrl, options, strValidate);
                    }
                }
            }
        }
    }

    /**
     * Exclude key to validate
     * @returns {number[]}
     */
    function excludeKeys() {
        //8 -> backspace | 9 -> Tab | 46 -> delete | 37, 38, 39, 40 -> arrows | 17 - Ctrl
        return [8, 9, 17];
    }

    /**
     * Exclude combination keys
     * @param event
     * @param options
     * @returns {boolean}
     */
    function excludeCombinationKeys(event, options) {
        var combinationExclude = false;
        //Ctrl exclusion
        if (event.ctrlKey) {
            combinationExclude = true;
        }
        if (event.shiftKey) {
            combinationExclude = false;
        }
        if (options.disableAltKey && event.altKey) {
            combinationExclude = false;
        }
        return combinationExclude;
    }

    /**
     * Check valid key
     * @param event
     * @param options
     * @returns {boolean}
     */
    function checkValidKey(event, options) {
        var validCheck = true;
        if (options.disableEnter && (event.keyCode == 13)) {
            validCheck = false;
        }
        return validCheck;
    }

    /**
     * Check key to include list
     * @param event
     * @param options
     * @returns {boolean}
     */
    function verifyKeyInExcludes(event, options) {
        return _.includes(excludeKeys(), event.keyCode) && !event.shiftKey;
    }

    /**
     * Check length text
     * @param value
     * @param maxLength
     * @returns {boolean}
     */
    function verifyLength(value, maxLength) {
        maxLength = maxLength || 0;
        var length = (value) ? value.toString().length : 0;
        return (length < maxLength || maxLength == 0);
    }
}
